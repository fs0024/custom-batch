/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.repository.batch;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.FileItems;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;

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



		public FileItems findByTaskItem(TaskItem items);
}
