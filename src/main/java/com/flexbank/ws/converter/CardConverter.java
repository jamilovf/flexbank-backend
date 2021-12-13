package com.flexbank.ws.converter;

import com.flexbank.ws.dto.CardDto;
import com.flexbank.ws.entity.Card;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class CardConverter {

    private final String CARD_STARS = "********";

    private ModelMapper modelMapper;

    @Autowired
    public CardConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CardDto entityToDto(Card card){
        CardDto cardDto = modelMapper.map(card, CardDto.class);

        String modifiedCardNumber = card.getCardNumber();
        modifiedCardNumber =
                modifiedCardNumber.substring(0,4) + CARD_STARS + modifiedCardNumber.substring(12);

        String modifiedExpiryDate = card.getExpiryDate().toString();
        modifiedExpiryDate =
                modifiedExpiryDate.substring(5,7) + "/" + modifiedExpiryDate.substring(2,4);

        cardDto.setCardNumber(modifiedCardNumber);
        cardDto.setExpiryDate(modifiedExpiryDate);

        return cardDto;
    }

    public Card dtoToEntity(CardDto cardDto){
        Card card = modelMapper.map(cardDto, Card.class);
        return card;
    }
}
