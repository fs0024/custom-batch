/**
 * 
 */
package com.trinet.benefits.oe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trinet.benefits.oe.entity.FileItems;

/**
 * @author imistry1
 *
 * 
 */
@Repository
public interface FileItemRepository extends JpaRepository<FileItems, String>{
	
	@Transactional
	public default void saveAllFileItems(List<FileItems> fileItemList) {
		this.saveAll(fileItemList);
	}

}
