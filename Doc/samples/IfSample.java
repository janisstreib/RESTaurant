String seminarLocation = "Feriburg";
boolean isFreiburg = "Freiburg".equals(seminarLocation);
if(isFreiburg) {
   System.out.println("Richtige Stadt!"); 
} else if("Karlsruhe".equals(seminarLocation)) {
   System.out.println("Karlsruhe ist auch nett."); 
} else {
    System.out.println("Haben Sie sich vertippt?");
}
