-- prendiamo il conteggio delle macchine per ora / giorno

SELECT FORMATDATETIME(ORARIO, 'yyyy-MM-dd') AS Giorno, HOUR(ORARIO) AS Ora, COUNT(*) AS NumeroEventi
FROM LOG_EVENTI
WHERE FORMATDATETIME(ORARIO, 'yyyy-MM-dd') = '2024-07-06'
GROUP BY FORMATDATETIME(ORARIO, 'yyyy-MM-dd'), HOUR(ORARIO)
ORDER BY Ora;