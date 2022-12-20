 def commastring = "" 
    dh = new File('.')
    dh.eachFile {
        
        if (it.name.startsWith("0")) {
            commastring = commastring +  "\"" + it.name + "\", "
        }
    }
    commastring = commastring.substring(0, commastring.length()-2);

def template = """
<!DOCTYPE html>

<html>
    <body onload="rotateIframe()">
        <iframe id="iframe" src="005-local-record" style="position:fixed; top:0; left:0; bottom:0; right:0; width:100%; height:100%; border:none; margin:0; padding:0; overflow:hidden; z-index:999999;"></iframe>

        <script>
            var arr = [ ${commastring}];
            var rotation = 15000;
            function rotateIframe() {
                for (var i=0;i<arr.length;i++) {
                    test(i);
                }
                //we're done here, schedule a refresh to start over 
                setTimeout(()=>location.reload(), rotation*arr.length);

            }
            

            



            async function test(i) {
                await delay(rotation*i);
                document.getElementById("iframe").src=arr[i] ;

            }
            
            function delay(time) {
                return new Promise(resolve => setTimeout(resolve, time));
            }
        </script>
    </body>

</html>
"""

    println template;
    File file = new File("index.html")
    file.write template;

    println file.text