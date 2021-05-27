package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * The enum Permission types.
 */
public enum PermissionTypes {
    /**
     * Owner permission types.
     */
    OWNER,
    /**
     * Partner permission types.
     */
    PARTNER,
    /**
     * Child permission types.
     */
    CHILD
}
