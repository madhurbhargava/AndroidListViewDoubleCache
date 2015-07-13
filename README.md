# AndroidListView

A ListView implementation with double cache. 
This listview implementation uses 2 caches, an LruCache(Cache-1) and a Disk cache(Cache-2). 

The architecture is such that the listview will tryo to fetch an image from the lru cache, if it fails, it will try the disk, 
if it fails again, it will use the executor framework to demand an async task. 
