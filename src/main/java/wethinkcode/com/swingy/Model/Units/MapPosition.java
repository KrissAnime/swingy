package wethinkcode.com.swingy.Model.Units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class MapPosition {
    private @Getter @Setter @NonNull int xPosition;
    private @Getter @Setter @NonNull int yPosition;
    private @Getter @Setter @NonNull int mapSize;

}
