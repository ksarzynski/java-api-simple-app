package quiz;

public class Question {

    int questionId;
    String bundleFieldName;
    int properAnswer;

    public Question(int questionId){
        this.questionId = questionId;
        bundleFieldName = "question" + questionId;
    }

    public String getBundleFieldName() {
        return bundleFieldName;
    }

    public int getProperAnswer() {
        return properAnswer;
    }

    public void setProperAnswer(int properAnswer) {
        this.properAnswer = properAnswer;
    }

    public int getQuestionId(){ return questionId; }
}
