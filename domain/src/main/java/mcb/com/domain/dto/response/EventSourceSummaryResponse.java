package mcb.com.domain.dto.response;

public class EventSourceSummaryResponse {
    private String label;
    private Long frequency;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }
}
