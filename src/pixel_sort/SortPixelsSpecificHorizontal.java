package pixel_sort;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;


public class SortPixelsSpecificHorizontal {
        public static String sep = File.separator;
    public static String folder = "C:" + sep + "Users" + sep + "Toms" + sep + "Desktop" + sep + "ImageEXPERIMENTS";

    public void sortSpecPixels(String fileName, String extention, String renameTo, int redLow, int greenLow, int blueLow, int redTop, int greenTop, int blueTop){
        File file = new File(folder + sep + fileName + "." + extention);
        List<Thread> list  = new ArrayList<>();

        try{
            BufferedImage img = ImageIO.read(file);
            int numOfRows = img.getHeight();
            /*
             * Instantiating each thread from the ThreadFunctionsClass
             */
            for(int y = 0; y < numOfRows; y++){
                ThreadFunctionsSomePix thrd = new ThreadFunctionsSomePix("Thread nr" + y, y, img, redLow, greenLow, blueLow, redTop, greenTop, blueTop);
                list.add(thrd);
                thrd.start();
                System.out.printf(" \n %s is sorting row nr %s", Thread.currentThread().getName(), y);
            }
            /*
            *the instantiated threads that were added to ArrayList are iterated over, to call the join() method on them to make the program waits 
            *until each of them finishes their respective row of pixels
            */
            for(Thread t : list){
                System.out.println("Waiting for " +  t.getName() + " to finish");
                t.join();
            }
            File tidyImage = new File(folder + sep + renameTo + "." + extention);
            ImageIO.write(img, extention, tidyImage);
            System.out.println("Sorted image save to location : " + tidyImage.getAbsolutePath());
        }catch(IOException | InterruptedException ex){
            ex.printStackTrace();
        }

    }

        public void sortAllPixels(String fileName, String extention, String renameTo){
        File file = new File(folder + sep + fileName + "." + extention);
        List<Thread> list  = new ArrayList<>();

        try{
            BufferedImage img = ImageIO.read(file);
            int numOfRows = img.getHeight();

            for(int y = 0; y < numOfRows; y++){
                ThreadFunctionsAllPix thrd = new ThreadFunctionsAllPix("Thread nr" + y, y, img);
                list.add(thrd);
                thrd.start();
                System.out.printf(" \n %s is sorting row nr %s", Thread.currentThread().getName(), y);
            }

            for(Thread t : list){
                System.out.println("Waiting for " +  t.getName() + " to finish");
                t.join();
            }
            File tidyImage = new File(folder + sep + renameTo + "." + extention);
            ImageIO.write(img, extention, tidyImage);
            System.out.println("Sorted image save to location : " + tidyImage.getAbsolutePath());
        }catch(IOException | InterruptedException ex){
            ex.printStackTrace();
        }

    }

    public class ThreadFunctionsSomePix extends Thread{
        public BufferedImage image;
        public int rowY;
        public int redLow;
        public int greenLow;
        public int blueLow;
        public int redTop;
        public int greenTop;
        public int blueTop;
 

        public ThreadFunctionsSomePix(String name){
            super(name);
        }

        public ThreadFunctionsSomePix(String name, int rowY, BufferedImage image, int redLow, int greenLow, int blueLow, int redTop, int greenTop, int blueTop){
            this(name);
            this.rowY = rowY;
            this.image = image;
            this.redLow = redLow;
            this.greenLow = greenLow;
            this.blueLow = blueLow;
            this.redTop = redTop;
            this.greenTop = greenTop;
            this.blueTop = blueTop;
        }


        @Override
        public void run(){
            int width = this.image.getWidth();
 //           int[] pixels = new int[width];
            PriorityQueue<Integer> pixels = new PriorityQueue<>((a, b) -> a - b);

            for(int x = 0; x < width; x ++){
                int onePixel = this.image.getRGB(x, rowY);
                int red = (onePixel >> 16) & 0xFF; //shift the bits that represent red by 16 and "AND" them with 255 (11111111) that way isolate the number to be just the last 8 bits
                int green = (onePixel >> 8) & 0xFF; //shift the bits that represent green by 8 and "AND" them with 255 (11111111) that way isolate the number to be just the last 8 bits
                int blue = (onePixel) & 0xFF; //no shift needed for blue just "AND" last 8 bits with 255 (11111111) that way isolate the number to be just the last 8 bits
                if(red > redLow && green > greenLow && blue > blueLow && red < redTop && green < greenTop && blue < blueTop){
                    pixels.add(onePixel);
                }
            }

            for(int x = 0; x < width; x++){
                int onePixel = this.image.getRGB(x, rowY);
                int red = (onePixel >> 16) & 0xFF;
                int green = (onePixel >> 8) & 0xFF; 
                int blue = (onePixel) & 0xFF;
                if(red > redLow && green > greenLow && blue > blueLow && red < redTop && green < greenTop && blue < blueTop){
                    this.image.setRGB(x, rowY, pixels.poll());
                }
            }
            
        }

    }
    //The inner class from SortPixelsThreadsHorizontal, did not know you could have more than one  inner class
    public class ThreadFunctionsAllPix extends Thread{
        public BufferedImage image;
        public int rowY;

 

        public ThreadFunctionsAllPix(String name){
            super(name);
        }

        public ThreadFunctionsAllPix(String name, int rowY, BufferedImage image){
            this(name);
            this.rowY = rowY;
            this.image = image;

        }

        /*
         * When thread is instantiated it has a row of pixels assigned to it by the calling method of the outer class to sort using quick sort algorithm
         */
        @Override
        public void run(){
            int width = this.image.getWidth();
            int[] pixels = new int[width];

            for(int x = 0; x < width; x ++){
                pixels[x] = this.image.getRGB(x, rowY); 
            }

            Sort.sortArr(pixels);

            for(int x = 0; x < width; x++){
                this.image.setRGB(x, rowY, pixels[x]);
            }
        }

    }

}
