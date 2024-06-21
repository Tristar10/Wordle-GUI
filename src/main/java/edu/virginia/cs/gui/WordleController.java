package edu.virginia.cs.gui;

import edu.virginia.cs.wordle.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.*;

public class WordleController {
    @FXML
    public Label char0=new Label(),char1=new Label(),char2=new Label(),char3=new Label(),char4=new Label();
    @FXML
    public Label char5=new Label(),char6=new Label(),char7=new Label(),char8=new Label(),char9=new Label();
    @FXML
    public Label char10=new Label(),char11=new Label(),char12=new Label(),char13=new Label(),char14=new Label();
    @FXML
    public Label char15=new Label(),char16=new Label(),char17=new Label(),char18=new Label(),char19=new Label();
    @FXML
    public Label char20=new Label(),char21=new Label(),char22=new Label(),char23=new Label(),char24=new Label();
    @FXML
    public Label char25=new Label(),char26=new Label(),char27=new Label(),char28=new Label(),char29=new Label();
    @FXML
    public Button yes = new Button(), no = new Button();
    @FXML
    public Label errorMsg= new Label();
    private int curPos;
    private int curRow;
    public static final int MAX_GUESSES = 6;
    private String answer;
    private int guessCount;
    private GameStatus gameStatus;
    private WordleDictionary legalDict;

    Wordle wordleGame;
    public void initialize(){
        yes.setVisible(false);
        no.setVisible(false);
        wordleGame=new WordleImplementation();
        curRow=0;
        curPos=0;
        for(int i=0;i<30;i++){
            getLabel(i).setText(" ");
            getLabel(i).setStyle("-fx-background-color: white; -fx-border-color: black;");
        }
        getDefaultDictionary();
    }

    public void getDefaultDictionary(){
        DefaultDictionaryFactory factory = new DefaultDictionaryFactory();
        WordleDictionary answersDic=factory.getDefaultAnswersDictionary();
        answer=answersDic.getRandomWord();
        legalDict=factory.getDefaultGuessesDictionary();
        guessCount=0;
        gameStatus=GameStatus.PLAYING;
        if(0==legalDict.getDictionarySize()){
            errorMsg.setText("Error: Cannot play Wordle with an Empty Dictionary");
            gameStatus=GameStatus.LOST;
        }
        if(!legalDict.containsWord(answer)){
            errorMsg.setText("Created a game with an illegal answer not found in the dictionary");
            gameStatus=GameStatus.LOST;
        }
    }

    public Label getLabel(int position){
        if(position==0){return char0;}
        else if(position==1){return char1;}
        else if(position==2){return char2;}
        else if(position==3){return char3;}
        else if(position==4){return char4;}
        else if(position==5){return char5;}
        else if(position==6){return char6;}
        else if(position==7){return char7;}
        else if(position==8){return char8;}
        else if(position==9){return char9;}
        else if(position==10){return char10;}
        else if(position==11){return char11;}
        else if(position==12){return char12;}
        else if(position==13){return char13;}
        else if(position==14){return char14;}
        else if(position==15){return char15;}
        else if(position==16){return char16;}
        else if(position==17){return char17;}
        else if(position==18){return char18;}
        else if(position==19){return char19;}
        else if(position==20){return char20;}
        else if(position==21){return char21;}
        else if(position==22){return char22;}
        else if(position==23){return char23;}
        else if(position==24){return char24;}
        else if(position==25){return char25;}
        else if(position==26){return char26;}
        else if(position==27){return char27;}
        else if(position==28){return char28;}
        else if(position==29){return char29;}
        else{throw new NoSuchElementException("");}
    }

    public void setChar(String input){
        getLabel(curPos).setText(input);
    }

    public void delCurChar(){
        if(curPos>curRow*5){
            curPos-=1;
            getLabel(curPos).setText(" ");
            errorMsg.setText("");

        }
    }
    public void addPos(){
        if(curPos<curRow*5+4){
            curPos+=1;
        }
    }

    public void submitGuess(){
        if(!getLabel(curRow*5+4).getText().equals(" ")){
            String guess=getLabel(curPos-4).getText()+getLabel(curPos-3).getText()+
                    getLabel(curPos-2).getText()+getLabel(curPos-1).getText()+getLabel(curPos).getText();
            //System.out.println(guess); //Testing
            handleGuess(guess);
            curRow ++;
            curPos ++;
        }
        if(curPos==30&&gameStatus!=GameStatus.WON) {
            gameStatus = GameStatus.LOST;
            conclusionPage();
        }
    }

    public void delLastChar()
    {
        getLabel(curPos).setText(" ");
        //delCurChar();
        curPos --;
        curRow --;
    }

    public void conclusionPage(){
        yes.setText("YES");
        no.setText("NO");
        yes.setOnAction(new yesButtonHandler());
        no.setOnAction(new noButtonHandler());
        yes.setVisible(true);
        no.setVisible(true);
        if(gameStatus==GameStatus.WON)
            errorMsg.setText("You Win! Wanna Play Again?");
        else
            errorMsg.setText("You Lose! The Correct Answer is: " + answer + ".\n                     Wanna Play Again?");
    }

    public void handleGuess(String guess) {
        if(!legalDict.containsWord(guess))
        {
            delLastChar();
            errorMsg.setText("Not a legal Word! Try Again!");
        }
        else if(guess.equals(answer)) {
            setCorrectAnswer();
            gameStatus = GameStatus.WON;
            conclusionPage();
        }
        else
            getWrongResult(guess);
    }

    private void setCorrectAnswer() {
        getLabel(curPos-4).setStyle("-fx-background-color: green; -fx-text-fill: white;");
        getLabel(curPos-3).setStyle("-fx-background-color: green; -fx-text-fill: white;");
        getLabel(curPos-2).setStyle("-fx-background-color: green; -fx-text-fill: white;");
        getLabel(curPos-1).setStyle("-fx-background-color: green; -fx-text-fill: white;");
        getLabel(curPos).setStyle("-fx-background-color: green; -fx-text-fill: white;");
    }

    private void getWrongResult(String guess) {
        for(int i = 0; i < guess.length(); i++)
            testIndividualChar(guess.charAt(i),i);
    }

    public void testIndividualChar(char input, int idx) {
        if(answer.charAt(idx)==Character.toUpperCase(input))
            getLabel(idx+curRow*5).setStyle("-fx-background-color: green; -fx-text-fill: white;");
        else if(answer.indexOf(Character.toUpperCase(input))<=4&&answer.indexOf(Character.toUpperCase(input))>=0)
            getLabel(idx+curRow*5).setStyle("-fx-background-color: yellow; -fx-text-fill: white;");
        else
            getLabel(idx+curRow*5).setStyle("-fx-background-color: grey; -fx-text-fill: white;");
    }

    protected enum GameStatus {
        PLAYING, WON, LOST;
    }

    public boolean terminate()
    {
        return GameStatus.WON==gameStatus||GameStatus.LOST==gameStatus;
    }

    private class noButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            System.exit(0);
        }
    }
    private class yesButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            gameStatus = GameStatus.PLAYING;
            initialize();
            errorMsg.setText("");
        }
    }
}
