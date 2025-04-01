package gitlet;

import java.io.IOException;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author Khanh Chung
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                Utils.message("Please enter a command.");
                System.exit(0);
            }

            String firstArg = args[0];
            switch (firstArg) {
                case "init":
                    Repository.init();
                    break;
                case "add":
                    Repository.add(args[1]);
                    break;
                case "commit":
                    Repository.commit(args[1]);
                    break;
                case "checkout":
                    if (args.length == 3) {
                        Repository.checkout(args[2]);
                    } else {
                        Repository.checkout(args[1], args[3]);
                    }
                    break;
                case "log":
                    Repository.log();
                    break;
                case "branch":
                    Repository.branch(args[1]);
                    break;
                case "rm-branch":
                    Repository.deleteBranch(args[1]);
                    break;
                case "global-log":
                    Repository.globalLog();
                    break;
                case "find":
                    Repository.find(args[1]);
                    break;
                default:
                    Utils.message("No command with that name exists.");
                    System.exit(0);
            }
        } catch (IOException e) {
            Utils.error(e.toString());
        }
    }
}
