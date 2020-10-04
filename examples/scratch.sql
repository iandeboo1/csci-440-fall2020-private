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