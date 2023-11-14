package christmas.domain.enumeration;

import java.util.Arrays;

public enum EventBadge {
    NONE("없음", 0, 4999),
    STAR("별", 5000, 9999),
    TREE("트리", 10000, 19999),
    SANTA("산타", 20000, Integer.MAX_VALUE);

    private final String name;
    private final Integer minimumBenefitAmount;
    private final Integer maximumBenefitAmount;

    EventBadge(String name, Integer minimumBenefitAmount, Integer maximumBenefitAmount) {
        this.name = name;
        this.minimumBenefitAmount = minimumBenefitAmount;
        this.maximumBenefitAmount = maximumBenefitAmount;
    }

    public static EventBadge findByBenefitByPrice(int benefitPrice) {
        return Arrays.stream(EventBadge.values())
                .filter(eventBadge -> eventBadge.minimumBenefitAmount <= benefitPrice
                        && eventBadge.maximumBenefitAmount >= benefitPrice)
                .findFirst()
                .orElse(NONE);
    }

    public String getName() {
        return name;
    }

}
