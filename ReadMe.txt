Faza 3


Per ekzekutimin e programit duhen keto librari: https://drive.google.com/file/d/1e9BSEdiO5vUvq5zjhTtr-cAI6bXqZwT-/view?usp=sharing, jane te vendosura si .rar dhe te uploduara ne google drive. Duhet te ekstraktohen ne ndonje follder. Jane librari per JJWT.

Pastaj per kompajllim duhet ne cmd te specifikohet claspath shembull: javac -cp C:\Users\Hp\Desktop\sigjars\jackson-annotations-2.9.0.jar;C:\Users\Hp\Desktop\sigjars\jackson-core-2.9.9.jar;C:\Users\Hp\Desktop\sigjars\jackson-databind-2.9.9.1.jar;C:\Users\Hp\Desktop\sigjars\jjwt-api-0.10.7.jar;C:\Users\Hp\Desktop\sigjars\jjwt-impl-0.10.7.jar;C:\Users\Hp\Desktop\sigjars\jjwt-jackson-0.10.7.jar;C:\Users\Hp\IdeaProjects\Sig2020\src Main.java

Ne program te gjith pathat jane absolut, por pothuajse ne te gjitha klasat ne fillim eshte nje variabel me emrin "destination" ose "lokacioni" psh(String lokacioni = "C:\\Users\\Hp\\IdeaProjects\\Sig2020\\";)   qe e permbane pathin absolute ne makinen time, per tu ekzekutuar programi ne makinen tuaj thjeshte ndryshojini keto variabla.

Dhe per per ekzekutim psh: java -cp C:\Users\Hp\Desktop\sigjars\jackson-annotations-2.9.0.jar;C:\Users\Hp\Desktop\sigjars\jackson-core-2.9.9.jar;C:\Users\Hp\Desktop\sigjars\jackson-databind-2.9.9.1.jar;C:\Users\Hp\Desktop\sigjars\jjwt-api-0.10.7.jar;C:\Users\Hp\Desktop\sigjars\jjwt-impl-0.10.7.jar;C:\Users\Hp\Desktop\sigjars\jjwt-jackson-0.10.7.jar;C:\Users\Hp\IdeaProjects\Sig2020\src Main create-user Filani, (ose) login Filani,  (ose) status "tokeni" , (ose) write-message Marresi "mesazhi" "tokeni i derguesit" , (ose) read-message "mesazhi".

Fjalekalimet ruhen ne Folderin Password, dhe tokenat ruhen ne folderin Tokens.

Te gjitha kerkesat e fazes se trete jane te implementuara:login, status , write-message, read-message.
