package com.nnk.springboot.repositories;

import com.nnk.springboot.data.BidData;
import com.nnk.springboot.data.CurvePointData;
import com.nnk.springboot.domain.CurvePoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CurvePointDaoIT {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Test
	@Sql(scripts = BidData.scriptClearDataBase)
	public void curvePointTest() {
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
		assertThat(curvePoint).isNotNull();
		assertThat(curvePoint).usingRecursiveComparison().isEqualTo(oldCurvePoint);

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);
		assertThat(listResult.get(0)).usingRecursiveComparison().isEqualTo(curvePoint);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> optCurvePoint = curvePointRepository.findById(id);
		assertThat(optCurvePoint).isEmpty();
	}
}
