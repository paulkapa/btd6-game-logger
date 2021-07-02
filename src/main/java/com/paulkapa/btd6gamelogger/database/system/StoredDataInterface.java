package com.paulkapa.btd6gamelogger.database.system;

import com.paulkapa.btd6gamelogger.models.system.SavedData;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoredDataInterface extends JpaRepository<SavedData, Integer> {}
