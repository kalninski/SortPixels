package pixel_sort;

//import java.time.Clock;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class SortPixelsThreadsHorizontal {


    public static String sep = File.separator;
    public static String folder = "C:" + sep + "Users" + sep + "Toms" + sep + "Desktop" + sep + "ImageEXPERIMENTS";

    public void sortAllPixels(String fileName, String extention, String renameTo){
        File file = new File(folder + sep + fileName + "." + extention);
        List<Thread> list  = new ArrayList<>();

        try{
            BufferedImage img = ImageIO.read(file);
            int numOfRows = img.getHeight();

            for(int y = 0; y < numOfRows; y++){
                ThreadFunctions thrd = new ThreadFunctions("Thread nr" + y, y, img);
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

    public class ThreadFunctions extends Thread{
        public BufferedImage image;
        public int rowY;

 

        public ThreadFunctions(String name){
            super(name);
        }

        public ThreadFunctions(String name, int rowY, BufferedImage image){
            this(name);
            this.rowY = rowY;
            this.image = image;

        }


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

