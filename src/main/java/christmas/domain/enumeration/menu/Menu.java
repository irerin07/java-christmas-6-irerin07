package christmas.domain.enumeration.menu;

import java.util.Optional;

public interface Menu {

    Optional<? extends Menu> findByName(String userInput);

    int calculateTotalPrice(int amount);

    String getMenuNameAndAmount(int amount);



}
