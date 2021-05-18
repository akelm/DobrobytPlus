package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


public enum PermissionTypes {
    OWNER,
    PARTNER,
    CHILD
}
