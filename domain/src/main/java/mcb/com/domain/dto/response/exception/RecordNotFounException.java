package mcb.com.domain.dto.response.exception;

public class RecordNotFounException extends RuntimeException{
    private String message;
    public RecordNotFounException(String message){
        super(message);
        this.message=message;
    }
    public RecordNotFounException(){

    }

}
