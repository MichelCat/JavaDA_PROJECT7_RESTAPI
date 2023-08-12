package com.nnk.poseidon.repositories;

import com.nnk.poseidon.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * CurvePointRepository is the interface that manages CurvePoint
 *
 * @author MC
 * @version 1.0
 */
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
