package wolox.training.models.dto;

import java.util.List;

public class ClassificationDTO {
    private List<String> lc_classifications;
    private List<String> dewey_decimal_class;

    public List<String> getLc_classifications() {
        return lc_classifications;
    }

    public void setLc_classifications(List<String> lc_classifications) {
        this.lc_classifications = lc_classifications;
    }

    public List<String> getDewey_decimal_class() {
        return dewey_decimal_class;
    }

    public void setDewey_decimal_class(List<String> dewey_decimal_class) {
        this.dewey_decimal_class = dewey_decimal_class;
    }
}
