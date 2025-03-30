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
            Utils.message("Please enter a command.");
            System.exit(0);
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                break;
            default:
                Utils.message("No command with that name exists.");
                System.exit(0);
        }
    }
}
