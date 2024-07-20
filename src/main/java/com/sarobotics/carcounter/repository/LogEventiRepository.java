package com.sarobotics.carcounter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sarobotics.carcounter.entity.LogEventi;

public interface LogEventiRepository extends CrudRepository<LogEventi, Long> {
	
	@Query(value = "SELECT FORMATDATETIME(e.orario, 'yyyy-MM-dd') AS Giorno, " +
	          "HOUR(e.orario) AS Ora, " +
	          "CAST(COUNT(*) AS INT) AS NumeroEventi, " +
	          "ROUND(CAST(COUNT(*) AS DOUBLE) / 60, 2) AS macchinealminuto " +
	          "FROM LOG_EVENTI e " +
	          "WHERE FORMATDATETIME(e.orario, 'yyyy-MM-dd') = :data " +
	          "GROUP BY FORMATDATETIME(e.orario, 'yyyy-MM-dd'), HOUR(e.orario) " +
	          "ORDER BY HOUR(e.orario)",
	          nativeQuery = true)
	   List<Object[]> countEventiPerOra(@Param("data") String data);

	   
	   @Query(value = "SELECT FORMATDATETIME(e.orario, 'yyyy-MM-dd') AS Giorno, " +
			   "CAST(COUNT(*) AS INT) AS NumeroEventi, " +
		          "ROUND(CAST(COUNT(*) AS DOUBLE) / 1440, 2) AS macchinealminuto " +
		          "FROM LOG_EVENTI e " +
		          "WHERE e.orario >= CURRENT_DATE - INTERVAL '7' DAY " +
		          "GROUP BY FORMATDATETIME(e.orario, 'yyyy-MM-dd') " +
		          "ORDER BY Giorno",
		          nativeQuery = true)
	   List<Object[]> eventiPerGiorno();
	   
	   @Query(value = "SELECT FORMATDATETIME(e.orario, 'yyyy-MM-dd') AS Giorno, " +
			   "CAST(COUNT(*) AS INT) AS NumeroEventi, " +
		          "ROUND(CAST(COUNT(*) AS DOUBLE) / 1440, 2) AS macchinealminuto " +
		          "FROM LOG_EVENTI e " +
		          "WHERE e.orario >= CURRENT_DATE - INTERVAL '14' DAY " +
		          "GROUP BY FORMATDATETIME(e.orario, 'yyyy-MM-dd') " +
		          "ORDER BY Giorno",
		          nativeQuery = true)
	   List<Object[]> eventiPer14Giorni();
	   
	    @Query(value = "SELECT COUNT(*) FROM LOG_EVENTI  WHERE FORMATDATETIME(orario, 'yyyy-MM-dd') = :giorno", nativeQuery = true)
	    int countByGiorno(String giorno);

}
