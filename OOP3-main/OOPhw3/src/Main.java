public class Main {

    public static void main(String[] args) {
        int a = 10;
        int b = 0;
        for(int i = 0 ; i<60; i++){
            int rolled = (int) ((Math.random() * (a+1)));
            System.out.print(rolled +"\t \t \t \t \t \t");
        }
    }
}