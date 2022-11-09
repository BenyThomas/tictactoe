package tictactoe;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    List<StringBuilder> list;
    private StringBuilder[] stringBuilders;
    private StringBuilder finalStr;
    private StringBuilder col0;
    private StringBuilder col1;
    private StringBuilder col2;
    private StringBuilder row0;
    private StringBuilder raw1;
    private StringBuilder raw2;
    private StringBuilder d0;
    private StringBuilder d2;
    private int xCounter;
    private int oCounter;
    private String inputString;
    private int code1;
    private int code2;
    Main() {
        row0 = new StringBuilder("");
        raw1 = new StringBuilder("");
        raw2 = new StringBuilder("");
        col0 = new StringBuilder("");
        col1 = new StringBuilder("");
        col2 = new StringBuilder("");
        d0 = new StringBuilder("");
        d2 = new StringBuilder("");
        list = new ArrayList<>();
        finalStr = new StringBuilder();
        code1 = 0;
        code2 = 0;
        inputString = "";
        xCounter = 0;
        oCounter = 0;
        stringBuilders = new StringBuilder[3];
    }

    public static void main(String[] args) {
        Main simple = new Main();
        simple.readUserInput();
    }

    public void readUserInput(){
        Scanner scanner = new Scanner(System.in);
        makeGrid("_________");
        formatInputString();
        displayGrid();
        String[] strings;
        boolean isX = true;
        while (getGameState() == 5) {
            strings = scanner.nextLine().split(" ");
            while (!isValidUserInput(strings)) {
                strings = scanner.nextLine().split(" ");
            }
            if(isX){
                playX();
                isX = false;
            }else {
                playO();
                isX = true;
            }
            displayGrid();
            formatInputString();

        }
        showState();
        colmString();

    }

    public boolean isValidUserInput(String[] str) {
        try {
            code1 = Integer.parseInt(str[0]);
            code2 = Integer.parseInt(str[1]);
        }catch (NumberFormatException nfe) {
            System.out.println("You should enter numbers!");
            return false;
        }
        if (!coordinatesAreInRange()) {
            System.out.println("Coordinates should be from 1 to 3!");
            return  false;
        } else if (isOccupied()) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;

        }
        return true;
    }

    public void playX() {

        getStringBuilders()[getCode1() - 1].replace(getCode2()-1,getCode2(), "X");

    }
    public void playO() {
        getStringBuilders()[getCode1() - 1].replace(getCode2()-1,getCode2(), "O");
    }
    public void showState() {
        if (getGameState() == 1) {
            System.out.println("Impossible");
        } else if (getGameState() == 2) {
            System.out.println("X wins");
        } else if (getGameState() == 3) {
            System.out.println("O wins");
        } else if (getGameState() == 4) {
            System.out.println("Draw");
        }
    }
    public boolean coordinatesAreInRange() {
        return (getCode1() >= 1 && getCode1() <= 3) && (getCode2() >= 1 && getCode2() <= 3);
    }

    public boolean isOccupied() {
        boolean isOccupied;
        isOccupied = !(getStringBuilders()[getCode1()-1].charAt(getCode2()-1) == '_');
        return isOccupied;
    }

    public void formatInputString() {
        rowString();
        colmString();
        diagonalString();
    }
    public boolean hasDifferentCount(String str) {
        int x= 0;
        int o = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.toUpperCase().charAt(i) == 'X') {
                x++;
            } else if (str.toUpperCase().charAt(i) == 'O') {
                o++;
            }
        }
        setxCounter(x);
        setoCounter(o);
        return (getxCounter() - getoCounter() >= 2) || (getoCounter() - getxCounter() >= 2);
    }

    private void diagonalString() {
        String d0= "", d1 ="";
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    d0+=getStringBuilders()[i].charAt(j);
                }
            }
            for (int k = 2; k >= 0; k--) {
                if ((i == 0 && k ==2) || (i == 1 && k == 1) || (i ==2 && k == 0)) {
                    d1+=getStringBuilders()[i].charAt(k);
                }
            }
        }
        setD0(new StringBuilder(d0));
        setD2(new StringBuilder(d1));
    }

    public boolean isXWin(StringBuilder builder) {
        return builder.toString().toUpperCase().contains("XXX");
    }
    public boolean isOWin(StringBuilder builder) {
        return builder.toString().toUpperCase().contains("OOO");
    }
    public boolean hasEmptyCell(StringBuilder builder) {
        return builder.toString().contains("_");
    }
    public boolean hasNoThreeInRow() {
        return !isXWin(getCol0()) && !isXWin(getCol1()) && !isXWin(getCol2());
    }

    public int getGameState() {
        int state = 0;
        boolean hasThreeXAndThreeOInRow = (isXWin(getCol0()) || isXWin(getCol1()) || isXWin(getCol2())) &&
                (isOWin(getCol0()) || isOWin(getCol1()) || isOWin(getCol2()))
                || hasDifferentCount(getInputString());
        boolean hasThreeX = isXWin(getRow0()) || isXWin(getRaw1()) || isXWin(getRaw2()) ||
                isXWin(getCol0()) || isXWin(getCol1()) || isXWin(getCol2()) ||
                isXWin(getD0()) || isXWin(getD2());
        boolean hasThreeO = isOWin(getRow0()) || isOWin(getRaw1()) || isOWin(getRaw2()) ||
                isOWin(getCol0()) || isOWin(getCol1()) || isOWin(getCol2()) || isOWin(getD0())
                || isOWin(getD2());
        if (hasThreeXAndThreeOInRow) {
            return state + 1; //System.out.println("Impossible");
        } else if (hasThreeX) {
            return state + 2; //System.out.println("X wins");
        }else if (hasThreeO) {
            return state + 3; //System.out.println("O wins");
        } else if (hasNoThreeInRow() && !(hasEmptyCell(getRow0()) || hasEmptyCell(getRaw1()) || hasEmptyCell(getRaw2()))){
            return state + 4; //System.out.println("Draw");
        }else if (hasNoThreeInRow() && (hasEmptyCell(getRow0()) || hasEmptyCell(getRaw1()) || hasEmptyCell(getRaw2()))){
            return state + 5; //System.out.println("Game not finished");
        } else {
            return state; //System.out.println("Impossible");
        }

    }
    public void makeGrid(String str) {
        List<String> row = new ArrayList<>();
        row.add(str.substring(0,3));
        row.add(str.substring(3,6));
        row.add(str.substring(6,9));
        StringBuilder[] sb = new StringBuilder[3];
        sb[0] = new StringBuilder(row.get(0));
        sb[1] = new StringBuilder(row.get(1));
        sb[2] = new StringBuilder(row.get(2));
        setStringBuilders(sb);

    }

    public void rowString() {
        setRow0(getStringBuilders()[0]);
        setRaw1(getStringBuilders()[1]);
        setRaw2(getStringBuilders()[2]);
    }
    public void colmString(){
        String s="", s1="", s2="";
        for (int j = 0; j < 3; j++) {

            for (int i = 0; i < 3; i++) {
                if (j ==0) {
                    s += getStringBuilders()[i].charAt(j);
                    setCol0(new StringBuilder(s));
                }else if (j == 1) {
                    s1 += getStringBuilders()[i].charAt(j);
                    setCol1(new StringBuilder(s1));
                }else {
                    s2 += getStringBuilders()[i].charAt(j);
                    setCol2(new StringBuilder(s2));
                }

            }

        }

    }

    public void  displayGrid() {
        StringBuilder s = getStringBuilders()[0];
        StringBuilder s1 = getStringBuilders()[1];
        StringBuilder s2 = getStringBuilders()[2];
        s.insert(1," ");
        s.insert(3," ");
        s1.insert(1," ");
        s1.insert(3," ");
        s2.insert(1," ");
        s2.insert(3," ");
        System.out.println("---------");
        System.out.printf("| %s |%n| %s |%n| %s |%n", s, s1, s2);
        System.out.println("---------");
        s.deleteCharAt(3);
        s.deleteCharAt(1);
        s1.deleteCharAt(3);
        s1.deleteCharAt(1);
        s2.deleteCharAt(3);
        s2.deleteCharAt(1);

    }
    public StringBuilder getCol0() {
        return col0;
    }

    public StringBuilder getCol1() {
        return col1;
    }

    public StringBuilder getCol2() {
        return col2;
    }

    public StringBuilder getRow0() {
        return row0;
    }

    public StringBuilder getRaw1() {
        return raw1;
    }

    public StringBuilder getRaw2() {
        return raw2;
    }

    public StringBuilder getD0() {
        return d0;
    }

    public StringBuilder getD2() {
        return d2;
    }

    public int getxCounter() {
        return xCounter;
    }

    public int getoCounter() {
        return oCounter;
    }

    public void setxCounter(int xCounter) {
        this.xCounter = xCounter;
    }

    public void setoCounter(int oCounter) {
        this.oCounter = oCounter;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public int getCode1() {
        return code1;
    }

    public void setCode1(int code1) {
        this.code1 = code1;
    }

    public int getCode2() {
        return code2;
    }

    public void setCode2(int code2) {
        this.code2 = code2;
    }

    public List<StringBuilder> getList() {
        return list;
    }

    public void setList(List<StringBuilder> list) {
        this.list = list;
    }

    public StringBuilder getFinalStr() {
        return finalStr;
    }

    public void setFinalStr(StringBuilder finalStr) {
        this.finalStr = finalStr;
    }

    public void setCol0(StringBuilder col0) {
        this.col0 = col0;
    }

    public void setCol1(StringBuilder col1) {
        this.col1 = col1;
    }

    public void setCol2(StringBuilder col2) {
        this.col2 = col2;
    }

    public void setRow0(StringBuilder row0) {
        this.row0 = row0;
    }

    public void setRaw1(StringBuilder raw1) {
        this.raw1 = raw1;
    }

    public void setRaw2(StringBuilder raw2) {
        this.raw2 = raw2;
    }

    public void setD0(StringBuilder d0) {
        this.d0 = d0;
    }

    public void setD2(StringBuilder d2) {
        this.d2 = d2;
    }

    public StringBuilder[] getStringBuilders() {
        return stringBuilders;
    }

    public void setStringBuilders(StringBuilder[] stringBuilders) {
        this.stringBuilders = stringBuilders;
    }
}
