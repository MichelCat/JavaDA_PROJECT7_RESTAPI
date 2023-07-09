package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CurvePointIT {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = CurvePoint.builder()
				.curveId(10)
				.term(10d)
				.value(30d)
				.build();

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
//		Assert.assertNotNull(curvePoint.getId());
//		Assert.assertTrue(curvePoint.getCurveId() == 10);
		assertThat(curvePoint).isNotNull();
		assertThat(curvePoint.getId()).isNotNull();
		assertThat(curvePoint.getCurveId()).isEqualTo(10);

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
//		Assert.assertTrue(curvePoint.getCurveId() == 20);
		assertThat(curvePoint).isNotNull();
		assertThat(curvePoint.getCurveId()).isEqualTo(20);

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
//		Assert.assertFalse(curvePointList.isPresent());
		assertThat(curvePointList).isEmpty();
	}
}
