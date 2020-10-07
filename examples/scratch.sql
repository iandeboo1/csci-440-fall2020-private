
SELECT * FROM artists
WHERE ArtistId=10;

SELECT tracks.Name, albums.Title
FROM tracks
         JOIN albums ON tracks.AlbumId = albums.AlbumId
         JOIN artists ON albums.ArtistId = artists.ArtistId
WHERE (tracks.Name LIKE '%war%' OR
       albums.Title LIKE '%great%' OR
       artists.Name LIKE );

SELECT * FROM tracks JOIN albums ON tracks.AlbumId = albums.AlbumId JOIN artists ON albums.ArtistId = artists.ArtistId WHERE (tracks.Name LIKE '%green%' OR albums.Title LIKE '%green%' OR artists.Name LIKE '%green%');

SELECT * FROM albums WHERE Title LIKE '%green%';

SELECT *
FROM tracks
    JOIN playlist_track ON tracks.TrackId = playlist_track.TrackID
    JOIN playlists ON playlist_track.PlaylistId = playlists.PlaylistId
WHERE playlists.PlaylistId = 3;

SELECT TrackId
FROM playlists
         JOIN playlist_track ON playlists.PlaylistId = playlist_track.PlaylistId
WHERE playlists.PlaylistId=3;

SELECT bosses.FirstName AS BossName, employees.FirstName AS Name
FROM employees
         JOIN employees AS bosses ON employees.ReportsTo = bosses.EmployeeId;

SELECT bosses.*
FROM employees
         JOIN employees AS bosses ON employees.ReportsTo = bosses.EmployeeId
WHERE employees.EmployeeId = 2;

SELECT FirstName
FROM employees
WHERE employees.ReportsTo = 2;

SELECT bosses.*
FROM employees
         JOIN employees AS bosses ON employees.ReportsTo = bosses.ReportsTo
WHERE employees.EmployeeId = 2;

SELECT *
FROM tracks

SELECT *
FROM invoice_items
JOIN tracks ON invoice_items.TrackId = tracks.TrackId
JOIN albums ON tracks.AlbumId = albums.AlbumId
JOIN artists ON albums.ArtistId = artists.ArtistId
WHERE InvoiceId=15;
