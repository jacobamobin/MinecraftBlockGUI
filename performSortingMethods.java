import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

class performSortingMethods {
    public String[] sortBlockParameter(String blockParameter, String sortOrder) {
        parserAndReadin parser = new parserAndReadin();
        ArrayList<Block> blocks = parser.readInData("data/Blocks.txt");

        switch (blockParameter.toLowerCase()) {
            case "name":
                blocks.sort(Comparator.comparing(Block::getName));
                break;
            case "renewability":
                blocks.sort(Comparator.comparing(Block::getRenewability));
                break;
            case "stackability":
                blocks.sort(Comparator.comparing(Block::getStackability));
                break;
            case "blastres":
                blocks.sort(Comparator.comparingDouble(Block::getBlastres));
                break;
            case "hardness":
                blocks.sort(Comparator.comparingDouble(Block::getHardness));
                break;
            case "luminous":
                blocks.sort(Comparator.comparing(Block::getLuminous));
                break;
            case "flammable":
                blocks.sort(Comparator.comparing(Block::getFlammable));
                break;
            case "dimension":
                blocks.sort(Comparator.comparing(Block::getDimension));
                break;
            default:
                System.out.println("Invalid parameter type for sorting.");
                return new String[0];
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            Collections.reverse(blocks);
        }

        String[] sortedListBlockNames = new String[blocks.size()];
        for (int i = 0; i < blocks.size(); i++) {
            sortedListBlockNames[i] = blocks.get(i).getName();
        }

        return sortedListBlockNames;
    }
}