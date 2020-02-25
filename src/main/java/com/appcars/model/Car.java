package com.appcars.model;

import com.appcars.model.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

   private String model;
   private BigDecimal price;
   private Color color;
   private int mileage;
   private List<String> components;


   @Override
   public String toString() {
      return "MODEL: " + model + ", " +
              "PRICE: " + price + ", " +
              "COLOR: " + color + ", " +
              "MILEAGE: " + mileage + ", " +
              "COMPONENTS: " + components + "\n";
   }
}
