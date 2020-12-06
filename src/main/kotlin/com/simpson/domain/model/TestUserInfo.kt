package com.simpson.domain.model;

import lombok.Data
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
class TestUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var uid: Long = 0L

    @Column(nullable = false)
    var umail: String = ""

    @Column(nullable = false)
    var password: String = ""
}