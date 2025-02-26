package pixel_sort;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class SortPixels {

    public BufferedImage image;
    public static String sep = File.separator;
	public static String folder = "C:" + sep + "Users" + sep + "Toms" + sep + "Desktop" + sep + "ImageEXPERIMENTS";

    public SortPixels(){}

    public void sortColumn(int x, int redLow, int greenLow, int blueLow, int redTop, int greenTop, int blueTop){
        int height = this.image.getHeight();
        PriorityQueue<Integer> pixels = new PriorityQueue<>((a, b) -> a - b);

        for(int y = 0; y < height; y++){
            int pixel = this.image.getRGB(x, y);
            int red = (pixel >> 16) & 0xFF;
            int green = (pixel >> 8) & 0xFF;
            int blue = (pixel) & 0xFF;
            if(red > redLow && green > greenLow && blue > blueLow && red < redTop && green < greenTop && blue < blueTop){
                pixels.add(pixel);
            }

        }
        
        int y1 = 0;
        while(!pixels.isEmpty()){
            int pixel = this.image.getRGB(x, y1);
            int red = (pixel >> 16) & 0xFF;
            int green = (pixel >> 8) & 0xFF;
            int blue = (pixel) & 0xFF;
            
            if(red > redLow && green > greenLow && blue > blueLow && red < redTop && green < greenTop && blue < blueTop){
                this.image.setRGB(x, y1, pixels.poll());
            }
            y1++;

        }

    }

    public void sortRow(int y,  int redLow, int greenLow, int blueLow, int redTop, int greenTop, int blueTop){
        int width = this.image.getWidth();
        PriorityQueue<Integer> pixels = new PriorityQueue<>((a, b) -> a - b);

        for(int x = 0; x < width; x++){
            int pixel = this.image.getRGB(x, y);
            int red = (pixel >> 16) & 0xFF;
            int green = (pixel >> 8) & 0xFF;
            int blue = (pixel) & 0xFF;
            if(red > redLow && green > greenLow && blue > blueLow && red < redTop && green < greenTop && blue < blueTop){
                pixels.add(pixel);
            }
        }

        int x1 = 0;
        while(!pixels.isEmpty()){
            int pixel = this.image.getRGB(x1, y);
            int red = (pixel >> 16) & 0xFF;
            int green = (pixel >> 8) & 0xFF;
            int blue = (pixel) & 0xFF;
            if(red > redLow && green > greenLow && blue > blueLow && red < redTop && green < greenTop && blue < blueTop){
                this.image.setRGB(x1, y, pixels.poll());
            }
            x1++;
        }
    }

    public void sortImageHorizontal(String fileName, String extention, String renameTo, int redLow, int greenLow, int blueLow, int redTop, int greenTop, int blueTop){
        File  file1 = new File(folder + sep + fileName + "." + extention);

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService exec = Executors.newFixedThreadPool(numThreads);

        try{
            BufferedImage img = ImageIO.read(file1);
            this.image = img;
            int height = img.getHeight();

            for(int y = 0; y < height; y++){
                final int row = y;
                exec.execute(() -> sortRow(row, redLow, greenLow, blueLow, redTop, greenTop, blueTop));
            }

            exec.shutdown();

            if(!exec.awaitTermination(15, TimeUnit.SECONDS)){
                System.out.println("Image could not be sorted in 15 seconds!");
                exec.shutdownNow();
            }
            ImageIO.write(img, extention, new File(folder + sep + renameTo + "." + extention));
        }catch(IOException | InterruptedException ex){
            ex.printStackTrace();
        }

    }




    public void sortImageVertical(String fileName, String extention, String renameTo, int redLow, int greenLow, int blueLow, int redTop, int greenTop, int blueTop){

        File  file1 = new File(folder + sep + fileName + "." + extention);

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService exec = Executors.newFixedThreadPool(numThreads);

        try{
            BufferedImage img = ImageIO.read(file1);
            this.image = img;
            int width = img.getWidth();

            for(int x = 0; x < width; x++){
                final int col = x;
                exec.execute(() -> sortColumn(col, redLow, greenLow, blueLow, redTop, greenTop, blueTop));
                System.out.println("Something is happening");
            }

            exec.shutdown();

            
                if(!exec.awaitTermination(15, TimeUnit.SECONDS)){
                    System.out.println(" Sorting this image takes more than 15 seconds");
                }
            ImageIO.write(image, extention, new File(folder + sep + renameTo + "." + extention));
        }catch(IOException | InterruptedException ex){
            ex.printStackTrace();
        }

    }


}
