package hello.itemservice.domain.web;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //lombok
public class BasicItemController {

    private final ItemRepository itemRepository;

    // MEMO: 상품 목록
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // MEMO: 상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * MEMO: 상품 등록
     * 요청 주소(URL)가 같아도 메서드 방식에 따라 기능을 구분할 수 있다.
     * @GetMapping() : 상품 등록 폼  (뷰 템플릿 반환)
     * @PostMapping() : 상품 등록 처리(폼 데이터 처리)
     */
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    @PostMapping("/add")
    // MEMO: add-item-v1
    // public String add(@RequestParam String itemName,
    //                   @RequestParam int price,
    //                   @RequestParam Integer quantity,
    //                   Model model) {
    //     Item item = new Item(itemName, price, quantity);
    //     item.setItemName(itemName);
    //     item.setPrice(price);
    //     item.setQuantity(quantity);
    //     itemRepository.save(item);
    //
    //     model.addAttribute("item", item);
    //     return "basic/item";
    // }
    // MEMO: add-item-v2 -> @ModelAttribute("값") 추가 시 모델에 자동 추가
    // @PostMapping("/add")
    // public String add(@ModelAttribute("item") Item item) {
    //     itemRepository.save(item);
    //     return "basic/item";
    // }
    // MEMO: add-item-v3 -> @ModelAttribute() 값 생략
    // public String add(@ModelAttribute Item item) {
    //     itemRepository.save(item);
    //     return "basic/item";
    // }
    // MEMO: add-item-v4 -> @ModelAttribute 생략
    // public String add(@ModelAttribute Item item) {
    //     itemRepository.save(item);
    //     return "basic/item";
    // }
    // MEMO: add-item-v5 -> v3 버전에서 redirect 기능 추가
    // public String add(@ModelAttribute Item item) {
    //     itemRepository.save(item);
    //     return "redirect:/basic/items/" + item.getId();
    // }
    // MEMO: [권장] add-item-v6 -> RedirectAttributes 추가
    public String add(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * MEMO: 상품 수정
     * 요청 주소(URL)가 같아도 메서드 방식에 따라 기능을 구분할 수 있다.
     * @GetMapping() : 상품 수정 폼  (뷰 템플릿 반환)
     * @PostMapping() : 상품 수정 처리(폼 데이터 처리)
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item updateParam) {
        Item item = itemRepository.findById(itemId);
        item.setItemName(updateParam.getItemName());
        itemRepository.update(itemId, updateParam);
        return "redirect:/basic/items/{itemId}";
    }

    //테스트용 데이터
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("새우깡", 1500, 3));
        itemRepository.save(new Item("감자깡", 1500, 5));
        itemRepository.save(new Item("옥수수깡", 2000, 5));
        itemRepository.save(new Item("고구마깡", 2000, 1));
    }
}
