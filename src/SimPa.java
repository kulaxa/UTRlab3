import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        for (String st : allInputs) {
            Stack<String> stack = new Stack<>();
            stack.push(startingStackSym);
            String currentState = startingState;
            boolean exist;
            AbstractMap.SimpleEntry<Boolean, String> result = new AbstractMap.SimpleEntry<>(null,null);
            print(stack,currentState);
            for (String inp : st.split(",")) {
                result =checkForTransition(stack, currentState,inp,transitions);
                currentState=result.getValue();
                exist=result.getKey();
                if(!exist){
                    break;
                }
            }
            if(result.getKey()){
                    boolean one = false;
                    for (String state : accepted) {
                        if (currentState.equals(state)) {
                            System.out.print("1");
                            one = true;
                            break;
                        }
                    }
                    if (!one) {
                        while(!one){
                        AbstractMap.SimpleEntry<Boolean, String> result2 = checkForEmpty(stack, currentState, transitions);
                        if (result2.getKey()) {
                            print(stack, result2.getValue());
                            currentState =result2.getValue();
                        } else {
                            break;

                        }
                            one=false;
                            for (String state : accepted) {
                                if (currentState.equals(state)) {
                                    one = true;
                                    break;
                                }
                            }
                        }
                        one=false;
                        for (String state : accepted) {
                            if (currentState.equals(state)) {
                                System.out.print("1");
                                one = true;
                                break;
                            }
                        }
                        if(!one){
                            System.out.print("0");
                        }
                    }
                    System.out.println();
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

    public static AbstractMap.SimpleEntry<Boolean, String> checkForEmpty(Stack<String> stack, String currentState, Map<String[], String[]> transitions) {
        boolean notDone = true;
        boolean result = false;
        while (notDone) {
            for (var entry : transitions.entrySet()) {
                if (entry.getKey()[0].equals(currentState) && entry.getKey()[1].equals("$")
                        && entry.getKey()[2].equals(stack.peek())) {
                    result = true;
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
            notDone = false;
        }
        return new AbstractMap.SimpleEntry<>(result, currentState);
    }

    public static AbstractMap.SimpleEntry<Boolean, String> checkForTransition(Stack<String> stack, String currentState, String sym, Map<String[], String[]> transitions) {
        boolean exist = false;
            if(stack.size()==0) {
                System.out.println("fail|0");
                return  new AbstractMap.SimpleEntry<>(false, currentState);}
            for (var entry : transitions.entrySet()) {
                if (entry.getKey()[0].equals(currentState) && entry.getKey()[1].equals(sym)
                        && entry.getKey()[2].equals(stack.peek())) {
                    exist = true;
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
        if (!exist) {
            AbstractMap.SimpleEntry<Boolean, String> result = checkForEmpty(stack, currentState, transitions);
            if (!result.getKey()) {
                System.out.println("fail|0");
                return new AbstractMap.SimpleEntry<>(false, currentState);
            }
            else{
                currentState=result.getValue();
                print(stack,currentState);
                result = checkForTransition(stack, currentState,sym,transitions);
            }
            return result;
        }
        else{
            print(stack,currentState);
        }
       return new AbstractMap.SimpleEntry<>(exist, currentState);
    }
}
