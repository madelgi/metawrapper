# MetaWrapper service

This small library provides a service for querying the MetaMap API.


## Installation

### Install Dependencies

To use metawrapper, you must first
1. Install metamap
2. Install mysql
3. Download and install metathesaurus, use database population scripts, use NLM source


### Run

From the root folder:

```bash
$ ./bin/metamap_start
$ mvn package
$ java -jar target/meta-wrapper-1.0-SNAPSHOT.jar server
```

## Usage


### MetaMap

**Example Query**:

```
http://<server>:<port>/metamap?query="<QUERY_HERE>"
```

**Example Response**:

```json
[
   {
      "acronyms":[

      ],
      "utterances":[
         {
            "id":"00000000.tx.1",
            "text":"\"lung cancer\"",
            "pcmList":[
               {
                  "candidates":[
                     {
                        "score":-1000,
                        "conceptId":"C0242379",
                        "conceptName":"Lung Cancer",
                        "preferredName":"Malignant neoplasm of lung",
                        "matchedWords":[
                           "lung",
                           "cancer"
                        ],
                        "semanticTypes":[
                           "neop"
                        ],
                        "head":true,
                        "overmatch":false,
                        "sources":[
                           "CCPSS",
                           "CHV",
                           "COSTAR",
                           "ICPC2P",
                           "LCH_NW",
                           "MDR",
                           "MEDCIN",
                           "MEDLINEPLUS",
                           "MSH",
                           "MTH",
                           "NCI",
                           "NCI_CTRP",
                           "NLMSubSyn",
                           "OMIM",
                           "RCD",
                           "RCDAE",
                           "SNMI",
                           "SNOMEDCT_US"
                        ],
                        "positions":[
                           {
                              "x":1,
                              "y":11
                           }
                        ]
                     },
                     {
                        "score":-1000,
                        "conceptId":"C0684249",
                        "conceptName":"LUNG CANCER",
                        "preferredName":"Carcinoma of lung",
                        "matchedWords":[
                           "lung",
                           "cancer"
                        ],
                        "semanticTypes":[
                           "neop"
                        ],
                        "head":true,
                        "overmatch":false,
                        "sources":[
                           "AOD",
                           "BI",
                           "CCPSS",
                           "CHV",
                           "COSTAR",
                           "CSP",
                           "CST",
                           "HPO",
                           "ICD10CM",
                           "ICPC2P",
                           "LNC",
                           "MDR",
                           "MEDCIN",
                           "MTH",
                           "NCI",
                           "NCI_CTEP-SDC",
                           "NCI_CTRP",
                           "NCI_NCI-GLOSS",
                           "NLMSubSyn",
                           "OMIM",
                           "PDQ",
                           "SNOMEDCT_US",
                           "WHO"
                        ],
                        "positions":[
                           {
                              "x":1,
                              "y":11
                           }
                        ]
                     },
		     ...,
                     {
                        "score":-716,
                        "conceptId":"C4315017",
                        "conceptName":"Pulmonary metastases of squamous cell carcinoma",
                        "preferredName":"Pulmonary metastases of squamous cell carcinoma",
                        "matchedWords":[
                           "pulmonary",
                           "metastases",
                           "of",
                           "squamous",
                           "cell",
                           "carcinoma"
                        ],
                        "semanticTypes":[
                           "fndg"
                        ],
                        "head":true,
                        "overmatch":false,
                        "sources":[
                           "OMIM"
                        ],
                        "positions":[
                           {
                              "x":1,
                              "y":11
                           }
                        ]
                     }
                  ],
                  "phrase":{
                     "mincoMan":"[punc([inputmatch([\"]),tokens([])]),head([lexmatch([lung cancer]),inputmatch([lung,cancer]),tag(noun),tokens([lung,cancer])]),punc([inputmatch([\"]),tokens([])])]",
                     "text":"\"lung cancer\"",
                     "pos":{
                        "x":0,
                        "y":13
                     }
                  }
               }
            ],
            "position":{
               "x":0,
               "y":13
            }
         }
      ]
   }
]
```


### MeSH IDs

**Example Query**

```
http://<server>:<port>/mesh?query="lung cancer"
```

**Example Response**

```json
[
   {
      "umlsId":"C0242379",
      "score":-1000,
      "entryTerms":{
         "D008175":[
            "Cancer, Lung",
            "Cancers, Lung",
            "Lung Cancers",
            "Cancer, Pulmonary",
            "Cancers, Pulmonary",
            "Pulmonary Cancers",
            "Cancer of Lung",
            "Cancer of the Lung",
            "Pulmonary Cancer",
            "Lung Cancer"
         ]
      }
   },
   {
      "umlsId":"C0006826",
      "score":-861,
      "entryTerms":{
         "D009369":[
            "Cancers",
            "Malignant Neoplasm",
            "Malignant Neoplasms",
            "Neoplasms, Malignant",
            "Neoplasm, Malignant",
            "Malignancy",
            "Malignancies",
            "Cancer"
         ]
      }
   },
   {
      "umlsId":"C0024109",
      "score":-861,
      "entryTerms":{
         "D008168":[
            "Lungs",
            "Lung"
         ]
      }
   },
   {
      "umlsId":"C0149925",
      "score":-805,
      "entryTerms":{
         "D055752":[
            "Small Cell Lung Carcinoma",
            "Small Cell Lung Cancer",
            "Oat Cell Carcinoma of Lung",
            "Oat Cell Lung Cancer",
            "Small Cell Cancer Of The Lung",
            "Carcinoma, Small Cell Lung"
         ]
      }
   },
   {
      "umlsId":"C0007097",
      "score":-768,
      "entryTerms":{
         "D002277":[
            "Carcinoma",
            "Carcinomas",
            "Epitheliomas",
            "Epithelial Neoplasm, Malignant",
            "Malignant Epithelial Neoplasm",
            "Malignant Epithelial Tumor",
            "Malignant Epithelial Tumors",
            "Neoplasm, Malignant Epithelial",
            "Tumor, Malignant Epithelial",
            "Epithelial Tumor, Malignant",
            "MALIGNANT EPITHELIAL NEOPL",
            "NEOPL MALIGNANT EPITHELIAL",
            "EPITHELIAL NEOPL MALIGNANT",
            "Malignant Epithelial Neoplasms",
            "Neoplasms, Malignant Epithelial",
            "Epithelial Neoplasms, Malignant",
            "Epithelial Tumors, Malignant",
            "Epithelioma"
         ]
      }
   },
   {
      "umlsId":"C0032285",
      "score":-768,
      "entryTerms":{
         "D011014":[
            "Pneumonia",
            "Pneumonias"
         ]
      }
   },
   {
      "umlsId":"C3714636",
      "score":-768,
      "entryTerms":{
         "D011014":[
            "Inflammation, Pulmonary",
            "Inflammations, Lung",
            "Inflammations, Pulmonary",
            "Pulmonary Inflammations",
            "Pneumonitis",
            "Lung Inflammations",
            "Inflammation, Lung",
            "Pneumonitides",
            "Lung Inflammation",
            "Pulmonary Inflammation"
         ]
      }
   }
]
```


