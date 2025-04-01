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
                    validateNumArgs(args, 1, "Incorrect operands.");
                    Repository.init();
                    break;
                case "add":
                    validateNumArgs(args, 2, "Incorrect operands.");
                    Repository.add(args[1]);
                    break;
                case "commit":
                    validateNumArgs(args, 2, "Incorrect operands.");
                    Repository.commit(args[1]);
                    break;
                case "checkout":
                    if (args.length == 2) {
                        Repository.checkoutFromBranch(args[1]);
                    } else if (args.length == 3) {
                        if (!args[1].equals("--")) {
                            Utils.message("Incorrect operands.");
                            System.exit(0);
                        }
                        Repository.checkout(args[2]);
                    } else if (args.length == 4 ) {
                        if (!args[2].equals("--")) {
                            Utils.message("Incorrect operands.");
                            System.exit(0);
                        }
                        Repository.checkout(args[1], args[3]);
                    } else {
                        Utils.error("Incorrect operands.");
                    }
                    break;
                case "log":
                    validateNumArgs(args, 1, "Incorrect operands.");
                    Repository.log();
                    break;
                case "branch":
                    validateNumArgs(args, 2, "Incorrect operands.");
                    Repository.branch(args[1]);
                    break;
                case "rm-branch":
                    validateNumArgs(args, 2, "Incorrect operands.");
                    Repository.deleteBranch(args[1]);
                    break;
                case "global-log":
                    validateNumArgs(args, 1, "Incorrect operands.");
                    Repository.globalLog();
                    break;
                case "find":
                    validateNumArgs(args, 2, "Incorrect operands.");
                    Repository.find(args[1]);
                    break;
                case "rm":
                    validateNumArgs(args, 2, "Incorrect operands.");
                    Repository.rm(args[1]);
                    break;
                case "status":
                    validateNumArgs(args, 1, "Incorrect operands.");
                    Repository.status();
                    break;
                case "reset":
                    validateNumArgs(args, 2, "Incorrect operands.");
                    Repository.reset(args[1]);
                    break;
                default:
                    Utils.message("No command with that name exists.");
                    System.exit(0);
            }
        } catch (IOException e) {
            Utils.error(e.toString());
        }
    }

    /**
     * Checks the number of arguments versus the expected number,
     * throws a RuntimeException if they do not match.
     *
     * @param args Argument array from command line
     * @param n Number of expected arguments
     */
    public static void validateNumArgs(String[] args, int n, String errorMessage) {
        if (!args[0].equals("init") && !Repository.isInitialized()) {
            Utils.message("Not in an initialized Gitlet directory.");
            System.exit(0);
        }

        if (args.length != n) {
            Utils.message(errorMessage);
            System.exit(0);
        }
    }
}
