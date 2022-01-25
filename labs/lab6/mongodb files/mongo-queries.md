Shayanna Gatchalian
10/30/2021
CMPE 172

Convert MySQL queries to MongoDB queries

1) Count of Records/Documents
    ```
    db.bios.find().count()
    ```

2) Find Bios with Birth Date before 1950
    ```
    db.bios.find({
        birth: {
            $lt: ISODate("1950-01-01T00:00:00.000Z")
        }
    }).pretty()
    ```

3) Get a unique listing of all the awards (in db/collection) granted
    ```
    db.bios.distinct("awards.award")
    ```

4) Get sorted listing of all first names(ascending order)
    ```
    db.bios.find({}, {"name.first": 1, _id: 0}).sort({"name.first": 1})
    ```

5) Get sorted listing of all first names(descending order)
    ```
    db.bios.find({}, {"name.first": 1, _id: 0}).sort({"name.first": -1})
    ```

6) Count number of bios that do not have an award yet
    ```
    db.bios.find({"awards": {$exists: false}}).count()
    ```

7) Display system ID (primary key) for bio in query #6
    ```
    db.bios.find({"awards": {$exists: false}}, {_id: 1})
    ```

8) Display names (first and last) along with awards and contributions from bios with 1 contribution AND 2 awards
    ```
    **was not able to fully answer this question**
    // count number of awards
    db.bios.aggregate(
        { $unwind: "$awards" },
        { $group: {
            _id: {$concat: ["$name.first", " ", "$name.last"]},
            totalawards: { $sum: 1 }
        }}
    )

    // count number of contribs
    db.bios.aggregate([
        {$project: { 
            totalcontribs: { $size: "$contribs" }
        }}
    ])
    ```

9) Display names (first and last) along with awards and contributions from bios with 1 contribution OR 2 awards
   ```
   **was not able to fully answer this question**
   // count number of awards
    b.bios.aggregate(
       { $unwind: "$awards" },
       { $group: {
           _id: {$concat: ["$name.first", " ", "$name.last"]},
           totalawards: { $sum: 1 }
       }}
   )

   // count number of contribs
   db.bios.aggregate([
       {$project: { 
           totalcontribs: { $size: "$contribs" }
       }}
   ])
   ```


10) List all awards for a bio
    ```
    db.bios.find(
        { },
        {
            name: {$concat: ["$name.first", " ", "$name.last"]},
            awards: {award: 1}, _id: 0
        }
    ).limit(1).pretty()
    ```
