package com.nnk.poseidon.repository;

import com.nnk.poseidon.data.CurvePointData;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.model.CurvePoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * CurvePointDaoIT is the integration test class handling Curve Point
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CurvePointDaoIT {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Test
	@Sql(scripts = GlobalData.scriptClearDataBase)
	void curvePointTest() {
		CurvePoint oldCurvePoint;
		CurvePoint curvePoint = CurvePointData.getCurvePointSource();

		// Save
		oldCurvePoint = curvePoint;
		curvePoint = curvePointRepository.save(curvePoint);
		assertThat(curvePoint).isNotNull();
		assertThat(curvePoint).usingRecursiveComparison().ignoringFields("id").isEqualTo(oldCurvePoint);
		assertThat(curvePoint.getId()).isEqualTo(1);

		// Update
		curvePoint.setCurveId(20);
		oldCurvePoint = curvePoint;
		curvePoint = curvePointRepository.save(curvePoint);
		assertThat(curvePoint).isNotNull()
								.isEqualTo(oldCurvePoint);

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		assertThat(listResult).isNotNull()
								.hasSize(1)
								.contains(curvePoint);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> optCurvePoint = curvePointRepository.findById(id);
		assertThat(optCurvePoint).isEmpty();
	}
}
