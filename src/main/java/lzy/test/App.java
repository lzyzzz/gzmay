package lzy.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class App {
    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            if (args[0].trim().toLowerCase().equals("--help") || args[0].trim().toLowerCase().equals("-h"))
                System.out.print("Please enter your digits with comma like the following example: 0,4,18");
            else
                new LetterPrinter(args[0].trim(),true).print();
        }
    }
}

class LetterPrinter {
    protected String[] digitLetters=new String[]{"","","abc","def","ghi","jkl","mno","pqrts","tuv","wxyz"};
    final String args;
    boolean supportTo99=false;

    public LetterPrinter(String args,boolean supportTo99 ) {
        this.args = args;
        this.supportTo99 =supportTo99;
    }

    public LetterPrinter(String args) {
        this.args = args;
    }
    public void print() {
        String[] representLetters;
        try {
            representLetters = getRepresentLetters();
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.printf("Please enter digit from 0 to %d.",digitLetters.length);
            return;
        }
        if (representLetters == null) {
            System.err.print("Represent letters not found!");
            return;
        }

        List[] letters = getLetters(representLetters);
        getCartesianProduct(letters).forEach(x-> {
            System.out.print(x);
            System.out.print(" ");
        });
    }

    private List<String> getCartesianProduct(List<String>... a) {
        if (a.length >= 2) {
            List<String> product = a[0];
            for (int i = 1; i < a.length; i++) {
                product = getCartesianProduct(product, a[i]);
            }
            return product;
        }

        return a[0];
    }

    private List<String> getCartesianProduct(List<String> a, List<String> b) {
        return Optional.of(a.stream()
                .map(e1 -> Optional.of(
                        b.stream()
                                .map(e2 ->
                                        Arrays.asList(e1, e2))
                                .collect(toList())).orElse(emptyList()))
                .flatMap(List::stream)
                .map(chars-> String.join("",chars) )
                .collect(toList())).orElse(emptyList());
    }

    private List[] getLetters(String[] representLetters) {
        return Arrays.asList(representLetters)
                .stream()
                .map(x -> x.chars()
                        .mapToObj(e->(char)e)
                        .map(e->e.toString())
                        .collect(Collectors.toList()))
                .collect(toList())
                .toArray(new List[0]);
    }

    private String[] getRepresentLetters() {

        return Arrays.stream(args.split(","))
                .map(x-> {
                            Integer i =Integer.parseInt(x);
                            if(i>=digitLetters.length && i<100 && supportTo99)
                                 return FIX_STRING_FOR_OVER_10;
                            return digitLetters[i];
                        }
                )
                .toArray(size->new String[size]);
    }

    final static String FIX_STRING_FOR_OVER_10="dsa";

}
