import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SimPa {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        String allInputs[] = line.split("\\|");
        line = reader.readLine();
        String states[] = line.split(",");
        line = reader.readLine();
        String symbols[] = line.split(",");
        line = reader.readLine();
        String stackSym[] = line.split(",");
        line = reader.readLine();
        String accepted[] = line.split(",");
        String startingState = reader.readLine();
        String startingStackSym = reader.readLine();

        Map<String[], String[]> transitions = new HashMap<>();
        line = reader.readLine();
        while (line != null) {

            String from[] = new String[3];
            String to[] = new String[2];
            from[0] = line.split("->")[0].split(",")[0];
            from[1] = line.split("->")[0].split(",")[1];
            from[2] = line.split("->")[0].split(",")[2];
            to[0] = line.split("->")[1].split(",")[0];
            to[1] = line.split("->")[1].split(",")[1];
            transitions.put(from, to);
            line = reader.readLine();
        }

        //transitions.values().forEach(k -> System.out.println(k[0] +" "+k[1].toCharArray()[0]));

        for (String st : allInputs) {
            Stack<String> stack = new Stack<>();
            stack.push(startingStackSym);
            String currentState = startingState;
            StringBuilder currentStateTemp = new StringBuilder();
            boolean exist = false;
            boolean notDone=true;

                for (String inp : st.split(",")) {
                    //while(notDone) {
                    print(stack, currentState);
                    exist = true;
                    for (var entry : transitions.entrySet()) {
                        if (entry.getKey()[0].equals(currentState) && entry.getKey()[1].equals(inp)
                                && entry.getKey()[2].equals(stack.peek())) {
                            exist = false;
                            // System.out.println();
                            //System.out.print("for: "+currentState+" and "+inp+"and "+stack.peek()+" pushing: ");
                            //System.out.println(entry.getValue()[1]);
                            // System.out.println();
                            stack.pop();
                            currentState = entry.getValue()[0];
                            for (int i = entry.getValue()[1].toCharArray().length - 1; i >= 0; i--) {
                                char c = entry.getValue()[1].toCharArray()[i];
                                if (c != '$') {
                                    stack.push(String.valueOf(c));
                                }
                            }
                            break;
                        }
                    }

                       // AbstractMap.SimpleEntry<Boolean, String> result = checkForEmpty(stack, currentState, transitions  );
                       // if(result.getKey()){
                       //     currentState=result.getValue();
                       //     notDone=true;
                       // }else{
                       //     notDone=false;
                       // }

                        if (exist) {
                            System.out.println("fail|0");
                            break;
                        }

                //}



            }
                if(!exist) {
                    AbstractMap.SimpleEntry<Boolean, String> result = checkForEmpty(stack, currentState, transitions);
                    currentState = result.getValue();
                    // if(!result.getKey()){


                    //if (exist || result.getKey()) {
                    boolean one = false;
                    for (String state : accepted) {
                        if (currentState.equals(state)) {
                            System.out.print("1");
                            one = true;
                            break;
                        }
                    }
                    if (!one) {
                        System.out.print("0");
                    }
                    System.out.println();
                    // }
                    // else{
                    //    System.out.println("0");
                    //}
                }
        }
    }

    public static void print(Stack<String> stack, String currentState) {
        System.out.print(currentState + "#");
        if (stack.size() == 0) {
            System.out.print("$");
            System.out.print("|");
            return;
        }
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.print(stack.get(i));
        }
        System.out.print("|");
    }

    public static AbstractMap.SimpleEntry<Boolean, String> checkForEmpty(Stack<String> stack, String currentState, Map<String[], String[]> transitions){
        boolean notDone = true;
        boolean result=false;
                while (notDone) {

                    for (var entry : transitions.entrySet()) {
                        if (entry.getKey()[0].equals(currentState) && entry.getKey()[1].equals("$")
                                && entry.getKey()[2].equals(stack.peek())) {
                            result=true;
                            stack.pop();
                            currentState = entry.getValue()[0];
                            for (int i = entry.getValue()[1].toCharArray().length - 1; i >= 0; i--) {
                                char c = entry.getValue()[1].toCharArray()[i];
                                if (c != '$') {
                                    stack.push(String.valueOf(c));
                                }
                            }

                            break;
                        }
                    }
                    print(stack, currentState);
                    notDone = false;
                }
         //print(stack, currentState);
                return new AbstractMap.SimpleEntry<>(result, currentState);
    }

}
