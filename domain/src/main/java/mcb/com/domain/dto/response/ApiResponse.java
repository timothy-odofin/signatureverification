package mcb.com.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @param <T>
 * @author odofintimothy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private int code;
    private T data;
    private Map<String, Object> meta = new HashMap<>();


    public Map<String, Object> getMeta() {
        return meta;
    }

    public ApiResponse addMeta(String key, Object value) {
        meta.put(key, value);
        return this;
    }

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message, int code, T data) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

}
