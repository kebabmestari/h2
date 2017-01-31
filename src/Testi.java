import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adminpc on 31/1/2017.
 */
public class Testi {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        Pattern p = Pattern.compile("(\\d{1,2})\\s*,?\\s*(\\d{1,2})");
        Matcher m = p.matcher(cin.next());
        while(m.find()) {
            System.out.println(m.group(2));
        }
    }
}
