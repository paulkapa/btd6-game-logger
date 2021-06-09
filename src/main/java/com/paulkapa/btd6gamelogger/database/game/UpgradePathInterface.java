package com.paulkapa.btd6gamelogger.database.game;

import com.paulkapa.btd6gamelogger.models.game.UpgradeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UpgradePathInterface extends JpaRepository<UpgradeEntity, Integer> {
    
}
