package hello.itemservice.domain.item;

import lombok.Data;

/**
 * @Data - @Setter, @Getter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode, @lombok.Value
 * - 실무에서는 핵심 모델에 @Data 사용 시 취약할 수 있으므로 @Getter, @Setter 만 사용
 */
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
