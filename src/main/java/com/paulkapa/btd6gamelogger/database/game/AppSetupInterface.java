package com.paulkapa.btd6gamelogger.database.game;

import com.paulkapa.btd6gamelogger.models.game.AppSetup;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppSetupInterface extends JpaRepository<AppSetup, Integer> {}
