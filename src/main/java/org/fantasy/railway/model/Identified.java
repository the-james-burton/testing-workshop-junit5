package org.fantasy.railway.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * superclass that provides a simple integer identifier
 */
@Data
@SuperBuilder
public abstract class Identified {

    Integer id;

}
