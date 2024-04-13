public class Program {
    public static void main(String[] args) throws Exception
    {
        //  java.io.Reader r = new java.io.StringReader
        //  (""
        //  +"func testfunc::num()\n"
        //  +"begin\n"
        //  +"    var a::num;\n"
        //  +"    a := 1;\n"
        //  +"    return a;\n"
        //  +"end\n"
        //  +"func main::num()\n"
        //  +"begin\n"
        //  +"    var a::num;\n"
        //  +"    a := testfunc();"
        //  +"    return 0;\n"
        //  +"end\n"
        //  );

         if(args.length == 0)
         args = new String[]
         {
             "D:\\Spring2024\\CMPSC470\\Proj5\\TestCases\\fail_08a.minc",
         };

        if(args.length <= 0)
            return;
        String minicpath = args[0];
        java.io.Reader r = new java.io.FileReader(minicpath);

        Compiler compiler = new Compiler(r);

        compiler.Parse();
    }
}
