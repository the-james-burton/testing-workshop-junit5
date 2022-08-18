package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * A station (node) on the network
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Station extends Identified {

    @Builder.Default
    private String name = "";

}
/*
Deephall
Freyton
Deepness
Blackcliff
Bayland
Lochbeach
Wellholt
Violethollow
Wellloch
Faypond
Watercliff
Roseash
Oldburn
Mallowbush
Ironhurst
Pryhill
Aldcrest
Deercliff
Westwitch
Woodlight
Deepshade
Mormont
Newsummer
Dellburn
Goldborough
Silverholt
Hollowpond
Starrysummer
Dragondell
Marblefay
Wheatmere
Southnesse
Wildemount
Rosemead
Oldbank
Wheatmead
Marbledragon
Clearcastle
Riverland
Prylyn
Winterton
Whitelight
Brighthill
Lightford
Erihill
Redtown
Roseden
Ironton
Oldfield
Bluesilver
Edgedale
Snowcourt
Witchmeadow
Winterrock
Rayacre
Stonehedge
Summerdragon
Orham
Esterpine
 */