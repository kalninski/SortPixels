package pixel_sort;

import java.time.Clock;

class Main {

public static void main(String[] args){
    Clock clock = Clock.systemDefaultZone();
    long mill1 = clock.millis();


    SortPixels pix = new SortPixels();
	pix.sortImageVertical("385888981_3009431119187812_9203513556659327631_n", "jpg", "PICTURE_VERTICAL", 50, 50, 50, 250, 250, 250);
    pix.sortImageHorizontal("385888981_3009431119187812_9203513556659327631_n", "jpg", "PICTURE_HORIZONTAL", 50, 50, 50, 250, 250, 250);
    long mill2 = clock.millis();
    System.out.println("Time to sort specific pixels only = " + (mill2 - mill1));
    }

}
