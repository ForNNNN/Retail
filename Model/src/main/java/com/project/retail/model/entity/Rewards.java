package com.project.retail.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Setter
@Getter
public class Rewards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "points")
    private Integer points;

    @Column(name = "reward_time")
    private Integer rewardTime;
}
