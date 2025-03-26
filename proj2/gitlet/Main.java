package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Khanh Chung
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Utils.error("Please enter a command.");
            System.exit(-1);
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                break;
            default:
                Utils.error("No command with that name exists.");
                System.exit(-1);
        }
    }
}
