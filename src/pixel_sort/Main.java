package pixel_sort;

import java.time.Clock;

class Main {

public static void main(String[] args){
    Clock clock = Clock.systemDefaultZone();
    long mill1 = clock.millis();


    SortPixels pix = new SortPixels();
    SortPixelsThreadsHorizontal sort = new SortPixelsThreadsHorizontal();
    SortPixelsSpecific sortSpec = new SortPixelsSpecific("C:\\Users\\Toms\\Desktop\\IU_SUBJECTS\\SEMESTER_2\\WEB_APPLICATION_DEVELOPMENT\\SortPixels\\SortPixels\\Images");
//	pix.sortImageVertical("16711535_699427440182219_5948146895090321023_n", "jpg", "ME_PICTURE_VERTICAL", 50, 50, 50, 250, 250, 250);
 //   pix.sortImageHorizontal("16711535_699427440182219_5948146895090321023_n", "jpg", "ME_PICTURE_HORIZONTAL", 50, 50, 50, 250, 250, 250);
 //  sort.sortAllPixels("382946456_731049572372895_1363472189920677209_n - Copy", "jpg", "!!!!HOUSE_PIC");
   sortSpec.sortSpecPixelsH("341687945_195927852907076_1479362320124839607_n", "jpg", "1", 50, 80, 80, 250, 250, 250);
   sortSpec.sortAllPixelsH("341687945_195927852907076_1479362320124839607_n", "jpg", "2");
   sortSpec.sortSpecPixelsV("341687945_195927852907076_1479362320124839607_n", "jpg", "3", 50, 80, 80, 250, 250, 250);
  sortSpec.sortAllPixelsV("341687945_195927852907076_1479362320124839607_n", "jpg", "4");
    long mill2 = clock.millis();

    System.out.println("Time to sort specific pixels only = " + (mill2 - mill1));
    }

}
