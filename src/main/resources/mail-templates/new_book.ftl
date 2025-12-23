<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Book Added</title>
</head>
<body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;">
    <table role="presentation" style="width: 100%; border-collapse: collapse; background-color: #f4f4f4;">
        <tr>
            <td style="padding: 20px 0; text-align: center;">
                <table role="presentation" style="width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                    <tr>
                        <td style="padding: 30px; text-align: center; background-color: #ffffff; border-radius: 8px 8px 0 0;">
                            <img src="https://raw.githubusercontent.com/VladPiatachenko/MPF_Labs/lab7-freemaker/web/src/main/resources/static/img/logo.png"
                                 style="width:150px; margin-bottom:20px;" alt="BookApp">
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 0 30px 20px 30px;">
                            <div style="background-color: #e8f4f8; border-left: 4px solid #2196F3; padding: 15px; margin-bottom: 20px; border-radius: 4px;">
                                <h1 style="margin: 0; color: #1976D2; font-size: 24px; font-weight: bold;">New Book Added to Catalog</h1>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 0 30px 20px 30px;">
                            <div style="background-color: #ffffff; border: 1px solid #e0e0e0; padding: 20px; border-radius: 4px;">
                                <h2 style="margin: 0 0 15px 0; color: #333333; font-size: 20px; font-weight: bold;">Book Details</h2>
                                <table role="presentation" style="width: 100%; border-collapse: collapse;">
                                    <tr>
                                        <td style="padding: 8px 0; color: #666666; font-weight: bold; width: 120px;">Title:</td>
                                        <td style="padding: 8px 0; color: #333333;">${title}</td>
                                    </tr>
                                    <tr>
                                        <td style="padding: 8px 0; color: #666666; font-weight: bold;">Author:</td>
                                        <td style="padding: 8px 0; color: #333333;">${author}</td>
                                    </tr>
                                    <tr>
                                        <td style="padding: 8px 0; color: #666666; font-weight: bold;">Year:</td>
                                        <td style="padding: 8px 0; color: #333333;">${year}</td>
                                    </tr>
                                    <tr>
                                        <td style="padding: 8px 0; color: #666666; font-weight: bold;">Added:</td>
                                        <td style="padding: 8px 0; color: #333333;">${added}</td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                    <#if year?int < 2000>
                    <tr>
                        <td style="padding: 0 30px 20px 30px;">
                            <p style="color: #a36b00; font-weight: bold; margin: 0; padding: 10px; background-color: #fff3cd; border: 1px solid #ffc107; border-radius: 4px;"><b>Rare Edition!</b></p>
                        </td>
                    </tr>
                    </#if>
                    <tr>
                        <td style="padding: 20px 30px; text-align: center; background-color: #f5f5f5; border-radius: 0 0 8px 8px; border-top: 1px solid #e0e0e0;">
                            <p style="margin: 0; color: #666666; font-size: 12px;">BookApp © 2025</p>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>

