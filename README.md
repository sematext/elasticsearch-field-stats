Field Stats Plugin for Elasticsearch
====================================

This plugin provides a feature very similar to Field Stats native
Elasticsearch functionality that has been removed from Elasticsearch starting
from version 6.0. It imitates the old functionality and is compatible with the
old API.

Installation
------------

Make sure to change the version number in the download URL based on your ES version. You can
find available releases here: https://github.com/sematext/elasticsearch-field-stats/releases. The
plugin version must match your Elasticsearch version. Feel free to contribute
new version if your version is not supported Elasticsearch version which you use.

```
bin/elasticsearch-plugin install URL
```

### Example for ES 6.4.1:


```
bin/plugin install https://github.com/sematext/elasticsearch-field-stats/releases/download/v6.4.1/field-stats-6.4.1.zip
```


Usage
-----

The field stats API lets one find statistical properties of a field
without executing an expensive aggregation by looking up measurements that
are natively available in the Lucene index. This can be useful to explore a
dataset that you don’t know much about. For example, this allows finding indices
that should be queried based on min/max date they store with meaningful.


```
GET logs*/_field_stats?fields=timestamp
```
 
Supported request options:


|Parameter|Description|
|-----|----
| `fields` | A list of fields to compute stats for. The field name supports wildcard notation. For example, using text_* will cause all fields that match the expression to be returned.|
| `level` | Defines if field stats should be returned on a per index level or on a cluster wide level. Valid values are indices and cluster (default). |


Alternatively, the fields option can also be defined in the request body:

```
POST _field_stats?level=indices
{
   "fields" : ["timestamp"]
}
```

Field statistics
----------------

The field stats API is supported on string-based, number-based and date-based fields and can return the following statistics per field:


|Parameter|Description|
|-----|----|
| `max_doc` | The total number of documents. |
| `doc_count` | The number of documents that have at least one term for this field, or -1 if this measurement isn’t available on one or more shards. |
| `density` | The percentage of documents that have at least one value for this field. This is a derived statistic and is based on the max_doc and doc_count.|
| `sum_doc_freq` | The sum of each term’s document frequency in this field, or -1 if this measurement isn’t available on one or more shards. Document frequency is the number of documents containing a particular term. |
| `sum_total_term_freq` | The sum of the term frequencies of all terms in this field across all documents, or -1 if this measurement isn’t available on one or more shards. Term frequency is the total number of occurrences of a term in a particular document and field. |
| ` min_value` | The lowest value in the field. |  
| `min_value_as_string` | The lowest value in the field represented in a displayable form. All fields, but string fields returns this. (since string fields, represent values already as strings) | 
| `max_value` | The highest value in the field. | 
| `max_value_as_string` | The highest value in the field represented in a displayable form. All fields, but string fields returns this. (since string fields, represent values already as strings) | 


### Note

It is very important to note that documents marked as deleted (but not
yet removed by internal Lucene merge process) still affect all the mentioned statistics. Keep this in
mind and use field stats wisely. This is very useful especially for environemnts with
append-only indices.  


Build and  contribution
-----------------------
In order to install this plugin, you need to create a zip distribution.

```
./gradlew build
```

This will produce a zip file in `build/distributions`. After building the zip file, you can install it like this

```
bin/plugin install file:///path/to/elasticsearch-field-stats/build/distribution/elasticsearch-field-stats-<VERSION>.zip
```

Feel free to prepare pull requests.



Licenses
--------
This repository in distributed under Apache License. Feel free to use and contribute!
